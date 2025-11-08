import {Route, Routes} from "react-router-dom";
import './App.css'
import TestPage from "./pages/TestPage.tsx";

function App() {
    return (
        <Routes>
            <Route  path={"/"} element={<TestPage/>} />
        </Routes>

    )
}
export default App;