import {Route, Routes, useLocation} from "react-router-dom";
import {Box, Toolbar, Typography} from "@mui/material";
import TopBarComponent from "./components/TopBarComponent.tsx";
import RegistrationPage from "./pages/RegistrationPage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import PasswordResetPage from "./pages/PasswordResetPage.tsx";
import ConfirmEmailPage from "./pages/ConfirmEmailPage.tsx";
import UserProfilePage from "./pages/UserProfilePage.tsx";
import MainFeedPage from "./pages/MainFeedPage.tsx";
import UploadPage from "./pages/UploadPage.tsx";
import PostPage from "./pages/PostPage.tsx";

function App() {
    const location = useLocation();
    const isHomePage = location.pathname === '/homepage';

    return (
        <Box sx={{ flexGrow: 1 }}>
            <TopBarComponent />

            <Toolbar />

            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    minHeight: '80vh',
                    textAlign: 'center'
                }}
            >
                {isHomePage ? (
                    <Typography
                        component="pre"
                        sx={{
                            fontFamily: 'monospace',
                            fontSize: '1.2rem',
                            color: 'primary.main',
                            lineHeight: 1.2,
                            fontWeight: 'bold'
                        }}
                    >
                        {/*goes hard B)))*/}
                        {`
    ______  _________________  ___________________
    ___   |/  /__  ____/__   |/  /__  ____/_  ___/
    __  /|_/ /__  __/  __  /|_/ /__  __/  _____ \\ 
    _  /  / / _  /___  _  /  / / _  /___  ____/ / 
    /_/  /_/  /_____/  /_/  /_/  /_____/  /____/  
                                                  
                                           
           (=^･ｪ･^=))ﾉ彡☆
    `}
                    </Typography>
                ) : (
                    <Routes>
                        <Route path={"/"} element={<MainFeedPage/>} />

                        <Route path={"/register"} element={<RegistrationPage/>} />

                        <Route path={"/login"} element={<LoginPage/>} />

                        <Route path="/forgot-password" element={<ForgotPasswordPage />} />

                        <Route path="/reset-password" element={<PasswordResetPage />} />

                        <Route path="/confirm-email" element={<ConfirmEmailPage />} />

                        <Route path={"/user/:username"} element={<UserProfilePage />} />

                        <Route path={"/upload"} element={<UploadPage />} />

                        <Route path={"/post/:id"} element={<PostPage />} />

                    </Routes>
                )}
            </Box>
        </Box>
    );
}
export default App;