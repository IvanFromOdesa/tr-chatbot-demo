import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import {createStoreContext} from "../common/stores/store.context.ts";
import {createStoreProvider} from "../common/stores/store.tsx";
import {AnonymousRootStore} from "../common/stores/anonymousRoot.store.ts";
import {init} from "../common/api/base.ts";
import App from "./App.tsx";
import {LOGIN_INIT_URL} from "../common/config.ts";
import {InitResponse} from "../common/types/init.ts";

const {useStoreContext, StoreContext} = createStoreContext<AnonymousRootStore>();
const StoreProvider = createStoreProvider(StoreContext);

const rootStore = new AnonymousRootStore(() => init(LOGIN_INIT_URL, InitResponse));

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <StoreProvider rootStore={rootStore}>
            <App />
        </StoreProvider>
    </StrictMode>
);

export {useStoreContext};
