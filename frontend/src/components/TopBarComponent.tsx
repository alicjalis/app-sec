import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";
import {useNavigate, useLocation} from "react-router-dom";

function TopBarComponent(){
    const navigate = useNavigate();
    const location = useLocation();
    const isRegisterPage = location.pathname === '/register';
    return (
        <AppBar position="fixed" color="primary" className="shadow-lg w-full top-0 left-0">
            <Toolbar className="px-6 flex justify-between items-center">

                <Typography
                    variant="h6"
                    component="div"
                    sx={{cursor: 'pointer'}}
                    className="tracking-wide font-semibold "
                    onClick={() => navigate('/')}
                >
                    memes
                </Typography>

                <Box sx={{ flexGrow: 1 }} />

                {!isRegisterPage && (
                    <Button variant="contained" color="secondary" onClick={() => navigate('/register')}>
                        Register
                    </Button>
                )}
            </Toolbar>
        </AppBar>
    );
}

export default TopBarComponent;