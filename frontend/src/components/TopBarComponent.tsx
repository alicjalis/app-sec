import {AppBar, Box, Button, IconButton, Toolbar, Typography, TextField, InputAdornment} from "@mui/material";
import {useNavigate, useLocation} from "react-router-dom";
import {GetCookie} from "../cookie/GetCookie.tsx";
import {useColorMode} from "../context/ColorModeContext.tsx";
import Brightness7Icon from "@mui/icons-material/Brightness7";
import Brightness4Icon from "@mui/icons-material/Brightness4";
import SearchIcon from "@mui/icons-material/Search";
import { UserProfileMenu } from "./UserProfileButtonComponent.tsx";
import { useState } from 'react';

function TopBarComponent(){
    const navigate = useNavigate();
    const location = useLocation();
    const isRegisterPage = location.pathname === '/register';
    const isLoginPage = location.pathname === '/login';
    const cookie = GetCookie();
    const { mode, toggleColorMode } = useColorMode();
    const [searchTerm, setSearchTerm] = useState("");

    const handleSearch = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') {
            navigate(`/?search=${encodeURIComponent(searchTerm)}`);
        }
    };

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

                <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center', mx: 2 }}>
                    <TextField
                        size="small"
                        placeholder="Search posts..."
                        variant="outlined"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        onKeyDown={handleSearch}
                        sx={{ backgroundColor: 'background.paper', borderRadius: 1, width: '50%', maxWidth: 400 }}
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <SearchIcon />
                                </InputAdornment>
                            ),
                        }}
                    />
                </Box>

                <Box sx={{ flexGrow: 1 }} />

                {cookie.logged && (
                    <Typography variant="h6" component="div" ml={1}>
                        Welcome {cookie.username}!
                    </Typography>
                )}

                <Box sx={{ flexGrow: 1 }} />

                <Box sx={{ display: 'flex', gap: 2 }}>

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

                    {cookie.logged && (
                        <Button variant="contained" color="secondary" onClick={() => navigate('/upload')}>
                            Upload
                        </Button>
                    )}

                    <IconButton color="inherit" onClick={toggleColorMode} >
                        {mode === 'dark' ? <Brightness7Icon /> : <Brightness4Icon />}
                    </IconButton>

                    {cookie.logged && <UserProfileMenu />}

                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default TopBarComponent;