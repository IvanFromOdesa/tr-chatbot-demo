import './QAUploadModal.css';
import {Bundle, EmployeePath, PathNavigation} from "../../common/types/ui.ts";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {Field, FieldArray, FieldProps, Formik} from "formik";
import React from "react";
import {qaUploadValidation} from "../schemas/qaUpload.validation.ts";
import {QAFormValues, QaValidation} from "../types/model.ts";
import {uploadQAs} from "../api/kb.ts";
import {getNestedError, getNestedTouched} from "../utils/util.ts";

interface QAUploadModalProps {
    show: boolean;
    handleClose: () => void;
    bundle: Bundle;
    qaValidation: QaValidation;
    pathNavigation: PathNavigation<EmployeePath> | null;
    setUploadStarted: (b: boolean) => void;
}

const CustomTextarea: React.FC<FieldProps & React.ComponentProps<typeof Form.Control>> = ({
                                                                                              field,
                                                                                              form: {touched, errors},
                                                                                              index,
                                                                                              ...props
                                                                                          }) => {

    const errorA = getNestedError(errors, `qas.${index}.a`);
    const touchedA = getNestedTouched(touched, `qas.${index}.a`);

    const isInvalid = !!errorA && !!touchedA;

    return (
        <Form.Control
            {...field}
            {...props}
            as="textarea"
            className="w-100 qa-input"
            style={{height: '120px', resize: 'none'}}
            isInvalid={isInvalid}
        />
    )
};

const QAUploadModal = ({
                           show,
                           handleClose,
                           bundle,
                           qaValidation,
                           pathNavigation,
                           setUploadStarted
                       }: QAUploadModalProps) => {
    const initialValues: QAFormValues = {
        qas: [{ q: "", a: "" }],
        translateToSupported: false
    };

    const handleSubmit = async (values: QAFormValues) => {
        await uploadQAs(values, pathNavigation?.knowledgeBaseUploadJson);
        setUploadStarted(true);
        handleClose();
    }

    return (
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>{bundle["page.view.qa.add"]}</Modal.Title>
            </Modal.Header>
            <Formik
                initialValues={initialValues}
                validationSchema={qaUploadValidation(bundle, qaValidation)}
                onSubmit={handleSubmit}
            >
                {({values, handleSubmit, errors, touched, setFieldValue, isSubmitting}) => (
                    <Form onSubmit={handleSubmit} noValidate>
                        <Modal.Body>
                            <Row style={{padding: '0 10px 10px 10px'}}>
                                <h5>
                                    {bundle?.['page.view.qa.modal.tips']}&nbsp;
                                    <a
                                        href={`${pathNavigation?.howTo}#QAs`}
                                        className="base-link"
                                    >
                                        {bundle?.['page.view.qa.modal.tipsLink']}
                                    </a>
                                </h5>
                            </Row>
                            <FieldArray name="qas">
                                {({push, remove}) => (
                                    <div style={{
                                        maxHeight: "300px",
                                        overflowY: "auto",
                                        overflowX: "hidden",
                                        padding: "10px"
                                    }}>
                                        {values.qas.map((_, index) => {
                                            const errorQ = getNestedError(errors, `qas.${index}.q`);
                                            const touchedQ = getNestedTouched(touched, `qas.${index}.q`);
                                            const errorA = getNestedError(errors, `qas.${index}.a`);

                                            return (
                                                <div key={`qa${index}`}>
                                                    <Row key={`q${index}`} className="mb-3">
                                                        <Col>
                                                            <Form.Label>
                                                                <strong>{bundle?.["page.view.qa.modal.q"]}</strong>
                                                            </Form.Label>
                                                            <Field
                                                                name={`qas.${index}.q`}
                                                                as={Form.Control}
                                                                placeholder={bundle?.['page.view.qa.model.q.example']}
                                                                isInvalid={!!errorQ && touchedQ}
                                                                className="qa-input"
                                                            />
                                                            <Form.Control.Feedback type="invalid">
                                                                {errorQ}
                                                            </Form.Control.Feedback>
                                                        </Col>
                                                    </Row>
                                                    <Row key={`a${index}`} className="mb-3">
                                                        <Col>
                                                            <Form.Label>
                                                                <strong>{bundle["page.view.qa.modal.a"]}</strong>
                                                            </Form.Label>
                                                            <Field
                                                                name={`qas.${index}.a`}
                                                                placeholder={bundle["page.view.qa.model.a.example"]}
                                                                component={CustomTextarea}
                                                                index={index}
                                                            />
                                                            <Form.Control.Feedback type="invalid">
                                                                {errorA}
                                                            </Form.Control.Feedback>
                                                        </Col>
                                                    </Row>
                                                    <Row key={`bd${index}`} className="mb-3">
                                                        <Col>
                                                            <Button
                                                                variant="danger"
                                                                onClick={() => remove(index)}
                                                                disabled={values.qas.length <= 1}
                                                            >
                                                                {bundle?.['action.delete']}
                                                            </Button>
                                                        </Col>
                                                    </Row>
                                                </div>
                                            )
                                        })}
                                        <Button variant="secondary" onClick={() => push({q: "", a: ""})}>
                                            {bundle["page.view.qa.modal.add"]}
                                        </Button>
                                    </div>
                                )}
                            </FieldArray>
                            <Form.Group controlId="translateToggle" className="mt-3">
                                <Form.Check
                                    type="switch"
                                    label={bundle["action.enableTranslation"]}
                                    onChange={(e) => setFieldValue("translateToSupported", e.target.checked)}
                                />
                            </Form.Group>
                        </Modal.Body>
                        <Modal.Footer className="d-flex justify-content-start">
                            <Button variant="primary" type="submit" disabled={isSubmitting}>
                                {bundle["action.upload"]}
                            </Button>
                        </Modal.Footer>
                    </Form>
                )}
            </Formik>
        </Modal>
    );
};

export default QAUploadModal;