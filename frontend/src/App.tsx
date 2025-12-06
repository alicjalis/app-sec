import {Route, Routes} from "react-router-dom";
import './App.css'
import TestPage from "./pages/TestPage.tsx";
import {Container} from "@mui/material";
import TopBarComponent from "./components/TopBarComponent.tsx";
import RegistrationPage from "./pages/RegistrationPage.tsx";

function App() {
    return (
        <Container>
            <TopBarComponent />
            <Routes>
                <Route path={"/"} element={<TestPage/>} />
                <Route path={"/register"} element={<RegistrationPage/>} />
            </Routes>

        </Container>

    )
}
export default App;