import {formatPageInfo} from "../../common/utils/format.ts";
import NoData from "./NoData.tsx";
import {Assets, Bundle} from "../../common/types/ui.ts";
import {WithPagination} from "../../common/types/pagination.ts";
import {OverlayTrigger, Tooltip} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faQuestion} from "@fortawesome/free-solid-svg-icons";
import {User} from "../../common/types/user.ts";

export function getPageData<T extends WithPagination<any>>(
    data: T,
    bundle: Bundle,
    assets: Assets | null,
    showMessage: string | undefined
) {
    return <h5 className="mb-4">
        {data.totalElements > 0 ? formatPageInfo(
            showMessage,
            data.pageable.pageNumber,
            data.pageable.pageSize,
            data.totalElements
        ) : <NoData
            imgUrl={assets?.['noData']}
            bundle={bundle}
        />}
    </h5>;
}

export function getUserOverlay(uploadedBy: User) {
    return <OverlayTrigger
        overlay={
            <Tooltip>
                {
                    uploadedBy.email
                }
            </Tooltip>
        }
    >
        <FontAwesomeIcon icon={faQuestion} style={{color: "#1294be"}}/>
    </OverlayTrigger>;
}