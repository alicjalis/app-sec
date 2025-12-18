import { Box, Button, TextField, Typography, Alert } from "@mui/material";
import { useForm } from "react-hook-form";
import { useState } from "react";

type ForgotPasswordData = {
    email: string;
};

function ForgotPasswordPage() {

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<ForgotPasswordData>();


    const [isSent, setIsSent] = useState(false);

    const onSubmit = (data: ForgotPasswordData) => {
        console.log("Email to reset password:", data.email);
        setIsSent(true);
    };

    return (
        <Box sx={{ maxWidth: 300, margin: '50px auto', display: 'flex', flexDirection: 'column', gap: 2 }}>
            <Typography variant="h5" textAlign="center">Reset Password</Typography>

            <Typography variant="body2" color="text.secondary" textAlign="center">
                Enter your email address and we'll send you a link to reset your password.
            </Typography>


            {isSent && (
                <Alert severity="success">
                    If an account exists for [mail] {isSent}, you will receive a password reset link shortly.
                </Alert>
            )}

            {!isSent && (
                <Box
                    component="form"
                    onSubmit={handleSubmit(onSubmit)}
                    sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
                >
                    <TextField
                        fullWidth
                        label="Email Address"
                        type="email"
                        {...register("email", {
                            required: "Email is required",
                            pattern: {
                                value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
                                message: "Please enter a valid email address"
                            }
                        })}
                        error={!!errors.email}
                        helperText={errors.email?.message}
                    />

                    <Button
                        fullWidth
                        type="submit"
                        variant="contained"
                    >
                        Send Reset Link
                    </Button>
                </Box>
            )}
        </Box>
    );
}

export default ForgotPasswordPage;