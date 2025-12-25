import { Box, Button, TextField, Typography, Alert, CircularProgress } from "@mui/material";
import { useForm, type SubmitHandler } from "react-hook-form";
import { REQUEST_PREFIX } from "../environment/Environment.tsx";
import { GetCookie, SetCookie } from "../cookie/GetCookie.tsx";
import type { Cookie } from "../cookie/Cookie";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { handleApiError} from "../utils/errorHandler.ts";
import { Link as RouterLink } from "react-router-dom";
import { Link } from "@mui/material";

type FormData = {
    username: string;
    password: string;
};

function LoginPage() {
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<FormData>();

    const cookie = GetCookie();
    const navigate = useNavigate();

    // API errors
    const [apiError, setApiError] = useState<string | null>(null);

    const onSubmit: SubmitHandler<FormData> = async (data) => {
        setApiError(null);

        try {
            const response = await fetch(REQUEST_PREFIX + 'auth/login', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const errorMessage = await handleApiError(response);
                throw new Error(errorMessage);
            }

            const result = await response.json();

            if (result) {
                const newCookie: Cookie = {
                    token: result.token,
                    expiresIn: result.expiresIn,
                    username: result.username,
                    userType: result.userType,
                    logged: true
                };
                SetCookie(newCookie);

                setTimeout(() => navigate("/"), 1000);
            }
        } catch (error: any) {
            setApiError(error.message || "Failed to connect to the server.");
        }
    };

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300, margin: '50px auto' }}>
            {!cookie.logged ? (
                <Box
                    component="form"
                    onSubmit={handleSubmit(onSubmit)}
                    sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
                >
                    <Typography variant="h5" textAlign="center">Login</Typography>

                    {apiError && <Alert severity="error">{apiError}</Alert>}

                    <TextField
                        label="Username"
                        {...register("username", {
                            required: "Username is required",
                            minLength: { value: 3, message: "Minimum 3 characters required" }
                        })}
                        error={!!errors.username}
                        helperText={errors.username?.message}
                        disabled={isSubmitting}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        {...register("password", {
                            required: "Password is required",
                            minLength: { value: 5, message: "Password must be at least 5 characters" }
                        })}
                        error={!!errors.password}
                        helperText={errors.password?.message}
                        disabled={isSubmitting}
                    />

                    <Link
                        component={RouterLink}
                        to="/forgot-password"
                        variant="body2"
                        sx={{ alignSelf: 'flex-end', mt: -1 }}
                    >
                        Forgot password?
                    </Link>

                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        disabled={isSubmitting}
                        startIcon={isSubmitting ? <CircularProgress size={20} color="inherit" /> : null}
                    >
                        {isSubmitting ? "Logging in..." : "Login"}
                    </Button>
                </Box>
            ) : (
                <Box sx={{ p: 3, border: '2px solid green', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="green">Logged in successfully!</Typography>
                    <Typography variant="h6">(｡◕‿‿◕｡)</Typography>
                </Box>
            )}
        </Box>
    );
}

export default LoginPage;