import {AppBar, Box, Button, IconButton, Toolbar, Typography} from "@mui/material";
import {useNavigate, useLocation} from "react-router-dom";
import {ClearCookie, GetCookie} from "../cookie/GetCookie.tsx";
import {useColorMode} from "../context/ColorModeContext.tsx";
import Brightness7Icon from "@mui/icons-material/Brightness7";
import Brightness4Icon from "@mui/icons-material/Brightness4";

function TopBarComponent(){
    const navigate = useNavigate();
    const location = useLocation();
    const isRegisterPage = location.pathname === '/register';
    const isLoginPage = location.pathname === '/login';
    const cookie = GetCookie();
    const { mode, toggleColorMode } = useColorMode();

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

                    <IconButton color="inherit" onClick={toggleColorMode}>
                        {mode === 'dark' ? <Brightness7Icon /> : <Brightness4Icon />}
                    </IconButton>

                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default TopBarComponent;