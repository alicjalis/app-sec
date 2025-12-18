import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";
import {useNavigate, useLocation} from "react-router-dom";
import {ClearCookie, GetCookie} from "../cookie/GetCookie.tsx";

function TopBarComponent(){
    const navigate = useNavigate();
    const location = useLocation();
    const isRegisterPage = location.pathname === '/register';
    const isLoginPage = location.pathname === '/login';
    const cookie = GetCookie();
    return (
        <AppBar position="fixed" color="primary" className="shadow-lg w-full top-0 left-0">
            <Toolbar className="px-6 flex justify-between items-center">
                <Typography
                    variant="h6"
                    component="div"
                    sx={{ cursor: 'pointer' }}
                    className="tracking-wide font-semibold"
                    onClick={() => navigate('/')}
                >
                    memes
                </Typography>

                <Box sx={{ flexGrow: 1 }} />

                <Box sx={{ display: 'flex', gap: 2 }}>
                    {cookie.logged === true && (
                        <Button variant="contained" color="secondary" onClick={() => { ClearCookie(); navigate('/') }}>
                            Logout
                        </Button>
                    )}

                    {!isLoginPage && !cookie.logged && (
                        <Button variant="contained" color="secondary" onClick={() => navigate('/login')}>
                            Login
                        </Button>
                    )}

                    {!isRegisterPage && !cookie.logged && (
                        <Button variant="contained" color="secondary" onClick={() => navigate('/register')}>
                            Register
                        </Button>
                    )}
                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default TopBarComponent;