import {Alert, Box, Button, CircularProgress, TextField, Typography} from "@mui/material";
import zxcvbn from "zxcvbn";
import PasswordStrengthMeter from "../components/PasswordStrengthMeter.tsx";
import {type SubmitHandler, useForm} from "react-hook-form";
import {useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {handleApiError} from "../utils/errorHandler.ts";
import {useSearchParams} from "react-router-dom";

type FormData = {
    newPassword: string;
    confirmPassword: string;
};

const MIN_PASSWORD_STRENGTH_SCORE = 2;

function PasswordResetPage(){
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
        watch,
        getValues,
    } = useForm<FormData>();

    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");
    const passwordValue = watch("newPassword");
    const [isResetSuccessful, setisResetSuccessful] = useState(false);

    const [apiError, setApiError] = useState<string | null>(null);

    const onSubmit: SubmitHandler<FormData> = async (data) => {
        setApiError(null);
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const { confirmPassword, ...dataToSend } = data;

        try {
            const response = await fetch(REQUEST_PREFIX + 'auth/reset-password?token=' + token, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
                body: JSON.stringify(dataToSend)
            });

            if (!response.ok) {
                const errorMessage = await handleApiError(response);
                throw new Error(errorMessage);
            }

            setisResetSuccessful(true);
            const result = await response.json();
            console.log("Success:", result);

        } catch (error: any) {
            setApiError(error.message || "An unexpected error occurred.");
        }
    }

    return (
        <Box
            sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300, margin: '50px auto' }}
        >
            {isResetSuccessful ? (
                <Box sx={{ p: 3, border: '2px solid green', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        Password Reset Successful!
                    </Typography>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        (｡◕‿‿◕｡)
                    </Typography>
                    <Typography variant="body1">
                        You can now log in.
                    </Typography>
                </Box>
            ) : (
                <Box
                    component="form"
                    onSubmit={handleSubmit(onSubmit)}
                    sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300 }}
                >
                    <Typography variant="h5" textAlign="center">Register</Typography>

                    {apiError && <Alert severity="error">{apiError}</Alert>}

                    {/*password*/}
                    <TextField
                        label="Password"
                        type="password"
                        {...register("newPassword", {
                            required: "Password is required",
                            validate: (value) => {
                                if (!value) return true;
                                const result = zxcvbn(value);
                                if (result.score < MIN_PASSWORD_STRENGTH_SCORE) {
                                    return `Password strength must be a least medium.`;
                                }
                                return true;
                            }
                        })}
                        disabled={isSubmitting}
                        required
                        error={!!errors.newPassword}
                        helperText={errors.newPassword ? errors.newPassword.message : null}
                    />

                    {passwordValue && (
                        <Box sx={{ mt: -1.5, mb: 1 }}>
                            <PasswordStrengthMeter password={passwordValue} />
                        </Box>
                    )}

                    {/*password confirmation*/}
                    <TextField
                        label="Confirm Password"
                        type="password"
                        {...register("confirmPassword", {
                            required: "Rewrite password",
                            validate: (value) =>
                                value === getValues('newPassword') || "Passwords must be the same",
                        })}
                        disabled={isSubmitting}
                        required
                        error={!!errors.confirmPassword}
                        helperText={errors.confirmPassword ? errors.confirmPassword.message : null}
                    />

                    <Button type="submit" variant="contained" color="primary" disabled={isSubmitting} startIcon={isSubmitting ? <CircularProgress size={20} color="inherit" /> : null} >
                        {isSubmitting ? "Resetting Password..." : "Reset Password"}
                    </Button>
                </Box>
            )}
        </Box>
    )
}

export default PasswordResetPage;