import {Bundle, EmployeePath, PathNavigation} from "../../common/types/ui.ts";
import {useRef} from "react";
import {ErrorMessage, Formik, FormikTouched} from "formik";
import {Button, Form, ListGroup, Modal} from "react-bootstrap";
import {pdfUploadValidation} from "../schemas/pdfUpload.validation.ts";
import {formatFileSize, parseFileSize} from "../../common/utils/format.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilePdf, faTrash} from "@fortawesome/free-solid-svg-icons";
import {uploadPdfs} from "../api/kb.ts";
import {PdfFormValues} from "../types/model.ts";

interface PdfUploadModalProps {
    show: boolean;
    handleClose: () => void;
    maxUploadFileSize: string;
    bundle: Bundle;
    uploadUrl: PathNavigation<EmployeePath>["knowledgeBaseUploadPdf"] | undefined;
    setUploadStarted: (b: boolean) => void;
}

const PdfUploadModal = ({show, handleClose, maxUploadFileSize, bundle, uploadUrl, setUploadStarted}: PdfUploadModalProps) => {
    // TODO: add later
    //const [uploadError, setUploadError] = useState<string | null>(null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);

    const handleFileUpload = async (values: PdfFormValues) => {
        await uploadPdfs(values.files, values.translate, uploadUrl);
        setUploadStarted(true);
        handleClose();
    }

    const handleRemoveFile = (indexToRemove: number, setFieldValue: (field: string, value: any) => void, files: FileList | null) => {
        if (!files) {
            return;
        }
        const dataTransfer = new DataTransfer();

        [...files].forEach((file, index) => {
            if (index !== indexToRemove) {
                dataTransfer.items.add(file);
            }
        });

        const newFiles = dataTransfer.files;
        setFieldValue("files", newFiles);

        if (newFiles.length === 0 && fileInputRef.current) {
            fileInputRef.current.value = "";
        }
    };

    return (
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>{bundle["page.view.pdf.upload"]}</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Formik<PdfFormValues>
                    initialValues={{ files: null, translate: false }}
                    initialTouched={{ files: true } as FormikTouched<PdfFormValues>}
                    validationSchema={pdfUploadValidation(bundle, parseFileSize(maxUploadFileSize), maxUploadFileSize)}
                    onSubmit={handleFileUpload}
                >
                    {({ handleSubmit, values, setFieldValue, isSubmitting, errors }) => (
                        <Form noValidate onSubmit={handleSubmit}>
                            <Form.Group controlId="formFile">
                                <Form.Label>{bundle["action.selectFromDevice"]}</Form.Label>
                                <Form.Control
                                    type="file"
                                    multiple
                                    accept="application/pdf"
                                    ref={fileInputRef}
                                    onChange={(e) => {
                                        const target = e.target as HTMLInputElement;
                                        if (target.files) {
                                            setFieldValue("files", target.files);
                                        } else {
                                            setFieldValue("files", null);
                                        }
                                    }}
                                    isInvalid={!!errors.files}
                                />
                                <ErrorMessage name="files" component="div" className="text-danger" />
                            </Form.Group>

                            {values.files && values.files.length > 0 && (
                                <div className="mt-3" style={{ maxHeight: "200px", overflowY: "auto" }}>
                                    <ListGroup>
                                        {Array.from(values.files).map((file: File, index) => (
                                            <ListGroup.Item key={index} className="d-flex align-items-center justify-content-between">
                                                <FontAwesomeIcon icon={faFilePdf} className="text-danger me-2" />
                                                <span className="flex-grow-1 text-truncate">
                                                    {file.name} ({formatFileSize(file.size, bundle)})
                                                </span>
                                                <Button
                                                    variant="outline-danger"
                                                    size="sm"
                                                    onClick={() => handleRemoveFile(index, setFieldValue, values.files)}
                                                >
                                                    <FontAwesomeIcon icon={faTrash} />
                                                </Button>
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                </div>
                            )}

                            <Form.Group controlId="translateToggle" className="mt-3">
                                <Form.Check
                                    type="switch"
                                    label={bundle["action.enableTranslation"]}
                                    onChange={(e) => setFieldValue("translate", e.target.checked)}
                                />
                            </Form.Group>

                            <div className="d-flex justify-content-start mt-3">
                                <Button type="submit" variant="primary" disabled={isSubmitting}>
                                    {bundle["action.upload"]}
                                </Button>
                            </div>
                        </Form>
                    )}
                </Formik>
            </Modal.Body>
        </Modal>
    )
}

export default PdfUploadModal;