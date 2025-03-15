import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';
import {createStoreContext} from "../common/stores/store.context.ts";
import {createStoreProvider} from "../common/stores/store.tsx";
import {VisitorRootStore} from "../common/stores/visitorRoot.store.ts";
import {init} from "../common/api/base.ts";
import {VISITOR_INIT_URL} from "../common/config.ts";
import {VisitorInitResponse} from "../common/types/init.ts";
import App from "./App.tsx";

const {useStoreContext, StoreContext} = createStoreContext<VisitorRootStore>();
const StoreProvider = createStoreProvider(StoreContext);

const rootStore = new VisitorRootStore(() => init(VISITOR_INIT_URL, VisitorInitResponse));

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <StoreProvider rootStore={rootStore}>
            <App />
        </StoreProvider>
    </StrictMode>
);

export {useStoreContext};
