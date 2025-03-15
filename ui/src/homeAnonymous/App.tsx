import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {observer} from "mobx-react";
import Home from "./components/Home.tsx";
import {useStoreContext} from "./homeAnonymous.tsx";
import {lazy} from "react";
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
                        <Home />
                        <Footer uiStore={uiStore} />
                    </>
            }
        </>
    )
}

export default observer(App);