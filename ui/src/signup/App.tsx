import 'bootstrap/dist/css/bootstrap.min.css';
import '../main.css';
import {lazy} from "react";
import {observer} from "mobx-react";
import Loading from "../common/components/Loading.tsx";
import {useStoreContext} from "./signup.tsx";
import Signup from "./components/Signup.tsx";
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
                        <Signup />
                        <Footer uiStore={uiStore} />
                    </>
            }
        </>
    )
}

export default observer(App);