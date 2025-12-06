import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";

function TopBarComponent(){
    const navigate = useNavigate();
    return (
        <AppBar position="fixed" color="primary" className="shadow-lg w-full top-0 left-0">
            <Toolbar className="px-6 flex justify-between items-center">
                <Typography
                    variant="h6"
                    className="tracking-wide font-semibold cursor-pointer"
                    onClick={() => navigate('/')}
                >
                    memes
                </Typography>
                <Box sx={{ flexGrow: 1 }} />
                <Button variant="contained" color="secondary" onClick={() => navigate('/register')}>
                    Register
                </Button>
            </Toolbar>
        </AppBar>
    );
}

export default TopBarComponent;