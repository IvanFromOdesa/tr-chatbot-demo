import 'bootstrap/dist/css/bootstrap.min.css';
import '../main.css';
import {lazy} from "react";
import {useStoreContext} from "./homeEmployee.tsx";
import Loading from "../common/components/Loading.tsx";
import {observer} from "mobx-react";
import KBMSection from "./components/KBMSection.tsx";
import ReportSection from "./components/ReportSection.tsx";
import {UiStore} from "../common/stores/ui.store.ts";
import {AllPaths} from "../common/types/ui.ts";
import EmployeeGreeting from "./components/EmployeeGreeting.tsx";

const Navbar = lazy(() => import("../common/components/Navbar.tsx"));
const Footer = lazy(() => import("../common/components/Footer.tsx"));

function App() {
    const employeeRootStore = useStoreContext();
    const { uiStore, authStore } = employeeRootStore;

    return (
        <>
            {
                employeeRootStore.loading ?
                    <Loading /> :
                    <>
                        <Navbar
                            uiStore={uiStore as UiStore<AllPaths>}
                            authStore={authStore}
                        />
                        <EmployeeGreeting />
                        <KBMSection />
                        <ReportSection />
                        <Footer uiStore={uiStore} />
                    </>
            }
        </>
    )
}

export default observer(App);