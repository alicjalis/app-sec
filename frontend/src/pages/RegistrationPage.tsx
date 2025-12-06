import {Box, Button, TextField, Typography} from "@mui/material";
import {type FormEvent, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";

function RegistrationPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        fetch(
            REQUEST_PREFIX + 'register',
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
                body: JSON.stringify({
                    "username": username,
                    "password": password
                })
            }
        ).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            console.log(data);
        })
    }
    return (
        <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300, margin: '50px auto' }}
        >
            <Typography variant="h5" textAlign="center">Register</Typography>
            <TextField
                label="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
            <TextField
                label="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            <Button type="submit" variant="contained" color="primary">
                Register
            </Button>
        </Box>
    )
}

export default RegistrationPage;