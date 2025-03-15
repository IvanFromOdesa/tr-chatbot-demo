import 'bootstrap/dist/css/bootstrap.min.css';
import '../main.css';
import {lazy} from "react";
import {useStoreContext} from "./login.tsx";
import {observer} from "mobx-react";
import Login from "./components/Login.tsx";
import Loading from "../common/components/Loading.tsx";
import {UiStore} from "../common/stores/ui.store.ts";
import {AllPaths} from "../common/types/ui.ts";

const Navbar = lazy(() => import("../common/components/Navbar.tsx"));
const Footer = lazy(() => import("../common/components/Footer.tsx"));

function App() {
    const anonymousRootStore = useStoreContext();
    const { uiStore} = anonymousRootStore;

    return (
        <>
            {
                anonymousRootStore.loading ?
                    <Loading /> :
                    <>
                        <Navbar uiStore={uiStore as UiStore<AllPaths>} />
                        <Login />
                        <Footer uiStore={uiStore} />
                    </>
            }
        </>
    )
}

export default observer(App);