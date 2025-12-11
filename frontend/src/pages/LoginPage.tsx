import {Box, Button, TextField, Typography} from "@mui/material";
import {useForm, type SubmitHandler} from "react-hook-form";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {GetCookie, SetCookie} from "../cookie/GetCookie.tsx";
import type {Cookie} from "../cookie/Cookie";
import {useNavigate} from "react-router-dom";

type FormData = {
    username: string;
    password: string;
};

function RegistrationPage() {
    const {
        register,

        handleSubmit,
        formState: { errors },
    } = useForm<FormData>();
    const cookie = GetCookie();
    const navigate = useNavigate();


    const onSubmit: SubmitHandler<FormData> = (data) => {

        fetch(
            REQUEST_PREFIX + 'auth/login',
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
                body: JSON.stringify(data)
            }
        ).then(response => {
            if (response.ok) {

                return response.json();
            }
        }).then(data => {
            if (data !== undefined) {
                const cookie: Cookie = {
                    token: data.token,
                    expiresIn: data.expiresIn,
                    username: data.username,
                    userType: data.userType,
                    logged: true
                }
                SetCookie(cookie);
                navigate("/login");
            }
        });
    }

    return (
        <Box
            sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300, margin: '50px auto' }}
        >
            {!cookie.logged ? (
                <Box
                    component="form"
                    onSubmit={handleSubmit(onSubmit)}
                    sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300 }}
                >
                    {/* TITLE */}
                    <Typography variant="h5" textAlign="center">Login</Typography>

                    {/* USERNAME */}
                    <TextField
                        label="Username"
                        {...register("username", {
                            required:"Type in username",
                        })}
                        required
                        error={!!errors.username}
                        helperText={errors.username ? errors.username.message : null}
                    />

                    {/* PASSWORD */}
                    <TextField
                        label="Password"
                        type="password"
                        {...register("password", {
                            required: "Password is required",
                        })}
                        required
                        error={!!errors.password}
                        helperText={errors.password ? errors.password.message : null}
                    />

                    {/* SUBMIT */}
                    <Button type="submit" variant="contained" color="primary">
                        Login
                    </Button>
                </Box>
            ) :(
                <Box sx={{ p: 3, border: '2px solid green', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        Login Successful!
                    </Typography>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        (｡◕‿‿◕｡)
                    </Typography>
                </Box>
            )}

        </Box>
    );
}

export default RegistrationPage;