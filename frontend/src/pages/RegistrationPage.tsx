import {Alert, Box, Button, CircularProgress, TextField, Typography} from "@mui/material";
import {useForm, type SubmitHandler} from "react-hook-form";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {useState} from "react";
import PasswordStrengthMeter from '../components/PasswordStrengthMeter.tsx';
import FillTestDataButton from "../components/FillTestDataButton.tsx";
import zxcvbn from "zxcvbn";
import { handleApiError } from "../utils/errorHandler.ts";

type FormData = {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
};


const MIN_PASSWORD_STRENGTH_SCORE = 2;



function RegistrationPage() {
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
        watch,
        getValues,
        setValue,
        setFocus
    } = useForm<FormData>();

    const passwordValue = watch("password");
    const [isRegistrationSuccessful, setIsRegistrationSuccessful] = useState(false);

    const [apiError, setApiError] = useState<string | null>(null);

    const onSubmit: SubmitHandler<FormData> = async (data) => {
        setApiError(null);
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const { confirmPassword, ...dataToSend } = data;

        try {
            const response = await fetch(REQUEST_PREFIX + 'auth/register', {
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

            setIsRegistrationSuccessful(true);
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
            {isRegistrationSuccessful ? (
                <Box sx={{ p: 3, border: '2px solid green', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        Registration Successful!
                    </Typography>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        (｡◕‿‿◕｡)
                    </Typography>
                    <Typography variant="body1">
                        You can now log in. Check your email to confirm it.
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

                    {/*username*/}

                    <TextField
                        label="Username"
                        {...register("username", {
                            required:"Type in username",

                            minLength: {
                                value: 5,
                                message: "Username must have minimum 5 characters"
                            },

                            pattern: {
                                value: /^[a-zA-Z0-9_]+$/,
                                message: "Username can only have letters, numbers and underscore(_)"
                            }
                        })}
                        disabled={isSubmitting}
                        required
                        error={!!errors.username}
                        helperText={errors.username ? errors.username.message : null}
                        slotProps={{
                            inputLabel: {
                                shrink: !!watch("username") || undefined
                            }
                        }}
                    />

                    {/*email*/}
                    <TextField
                        label="Email"
                        {...register("email", {
                            required:"Type in email",

                            pattern: {
                                value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
                                message: "Invalid email"
                            }
                        })}
                        disabled={isSubmitting}
                        required
                        error={!!errors.email}
                        helperText={errors.email ? errors.email.message : null}
                        slotProps={{
                            inputLabel: {
                                shrink: !!watch("email") || undefined
                            }
                        }}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        {...register("password", {
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
                        error={!!errors.password}
                        helperText={errors.password ? errors.password.message : null}
                        slotProps={{
                            inputLabel: {
                                shrink: !!watch("password") || undefined
                            }
                        }}
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
                                value === getValues('password') || "Passwords must be the same",
                        })}
                        disabled={isSubmitting}
                        required
                        error={!!errors.confirmPassword}
                        helperText={errors.confirmPassword ? errors.confirmPassword.message : null}
                        slotProps={{
                            inputLabel: {
                                shrink: !!watch("password") || undefined
                            }
                        }}
                    />

                    <FillTestDataButton
                        setValue={setValue}
                        setFocus={setFocus}
                    />

                    <Button type="submit" variant="contained" color="primary" disabled={isSubmitting} startIcon={isSubmitting ? <CircularProgress size={20} color="inherit" /> : null} >
                        {isSubmitting ? "Registering..." : "Register"}
                    </Button>
                </Box>
            )}
        </Box>
    );
}

export default RegistrationPage;