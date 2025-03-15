import {PUBLIC_URL} from "../../common/config.ts";
import {Bundle} from "../../common/types/ui.ts";

interface NoDataProps {
    imgUrl: string | undefined;
    bundle: Bundle;
}

const NoData = ({imgUrl, bundle}: NoDataProps) => {
    return (
        <>
            <div className="text-center">
                <div className="d-flex justify-content-center align-items-center">
                    <img src={`${PUBLIC_URL}${imgUrl}`} alt="noData" style={{
                        maxWidth: '100%',
                        maxHeight: '250px',
                        width: 'auto'
                    }}/>
                </div>
                <div style={{
                    fontStyle: 'italic',
                    marginTop: '20px'
                }}>
                    {bundle?.['action.empty']}
                </div>
            </div>
        </>
    )
}

export default NoData;