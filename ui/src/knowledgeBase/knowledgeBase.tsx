import {createStoreContext} from "../common/stores/store.context.ts";
import {createStoreProvider} from "../common/stores/store.tsx";
import {init} from "../common/api/base.ts";
import {KB_INIT_URL} from "../common/config.ts";
import {createRoot} from "react-dom/client";
import {StrictMode} from "react";
import App from "./App.tsx";
import {KnowledgeBaseRootStore} from "./stores/knowledgeBaseRoot.store.ts";
import {KnowledgeBaseInitResponse} from "./types/init.ts";
import {QueryClientProvider} from "@tanstack/react-query";
import {TANSTACK_QUERY_CLIENT} from "../common/config/tanstack.query.client.ts";

const {useStoreContext, StoreContext} = createStoreContext<KnowledgeBaseRootStore>();
const StoreProvider = createStoreProvider(StoreContext);

const rootStore = new KnowledgeBaseRootStore(() => init(KB_INIT_URL, KnowledgeBaseInitResponse));

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <StoreProvider rootStore={rootStore}>
            <QueryClientProvider client={TANSTACK_QUERY_CLIENT}>
                <App />
            </QueryClientProvider>
        </StoreProvider>
    </StrictMode>
);

export {useStoreContext};