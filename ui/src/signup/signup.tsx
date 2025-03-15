import {createStoreContext} from "../common/stores/store.context.ts";
import {AnonymousRootStore} from "../common/stores/anonymousRoot.store.ts";
import {createStoreProvider} from "../common/stores/store.tsx";
import {init} from "../common/api/base.ts";
import {createRoot} from "react-dom/client";
import {StrictMode} from "react";
import App from "./App.tsx";
import {SIGNUP_INIT_URL} from "../common/config.ts";
import {InitResponse} from "../common/types/init.ts";

const {useStoreContext, StoreContext} = createStoreContext<AnonymousRootStore>();
const StoreProvider = createStoreProvider(StoreContext);

const rootStore = new AnonymousRootStore(() => init(SIGNUP_INIT_URL, InitResponse));

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <StoreProvider rootStore={rootStore}>
            <App />
        </StoreProvider>
    </StrictMode>
);

export {useStoreContext};