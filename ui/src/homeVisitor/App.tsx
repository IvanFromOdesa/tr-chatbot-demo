import 'bootstrap/dist/css/bootstrap.min.css';
import '../main.css';
import {lazy} from "react";
import {observer} from "mobx-react";
import {useStoreContext} from "./homeVisitor.tsx";
import Loading from "../common/components/Loading.tsx";
import {UiStore} from "../common/stores/ui.store.ts";
import {AllPaths} from "../common/types/ui.ts";
import ChatComponent from "./components/ChatComponent.tsx";

const Navbar = lazy(() => import("../common/components/Navbar.tsx"));
const Footer = lazy(() => import("../common/components/Footer.tsx"));

function App() {
    const visitorRootStore = useStoreContext();
    const {uiStore, authStore} = visitorRootStore;

    return (
        <>
            {
                visitorRootStore.loading ?
                    <Loading /> :
                    <>
                        <Navbar
                            uiStore={uiStore as UiStore<AllPaths>}
                            authStore={authStore}
                        />
                        <ChatComponent />
                        <Footer uiStore={uiStore} />
                    </>
            }
        </>
    )
}

export default observer(App);