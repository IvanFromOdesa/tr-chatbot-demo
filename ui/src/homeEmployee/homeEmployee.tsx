import {StrictMode} from 'react';
import { createRoot } from 'react-dom/client';
import {createStoreContext} from "../common/stores/store.context.ts";
import {EmployeeRootStore} from "../common/stores/employeeRoot.store.ts";
import {createStoreProvider} from "../common/stores/store.tsx";
import {EMPLOYEE_INIT_URL} from "../common/config.ts";
import {EmployeeInitResponse} from "../common/types/init.ts";
import {init} from "../common/api/base.ts";
import App from "./App.tsx";

const {useStoreContext, StoreContext} = createStoreContext<EmployeeRootStore>();
const StoreProvider = createStoreProvider(StoreContext);

const rootStore = new EmployeeRootStore(() => init(EMPLOYEE_INIT_URL, EmployeeInitResponse));

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <StoreProvider rootStore={rootStore}>
            <App />
        </StoreProvider>
    </StrictMode>
);

export {useStoreContext};