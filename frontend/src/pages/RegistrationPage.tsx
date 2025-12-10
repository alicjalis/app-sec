import {Box, Button, TextField, Typography} from "@mui/material";
import {useForm, type SubmitHandler} from "react-hook-form";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {useState} from "react";

type FormData = {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
};

function RegistrationPage() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        watch,
        getValues
    } = useForm<FormData>();

    const passwordValue = watch("password");
    const [isPasswordFocused, setIsPasswordFocused] = useState(false);
    const [isRegistrationSuccessful, setIsRegistrationSuccessful] = useState(false);

    const validationCriteria = {
        lowercase: /[a-z]/.test(passwordValue),
        uppercase: /[A-Z]/.test(passwordValue),
        number: /[0-9]/.test(passwordValue),
        minLength: passwordValue ? passwordValue.length >= 8 : false,
    };

    const onSubmit: SubmitHandler<FormData> = (data) => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const { confirmPassword, ...dataToSend } = data;

        fetch(
            REQUEST_PREFIX + 'register',
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
                body: JSON.stringify(dataToSend)
            }
        ).then(response => {
            if (response.ok) {
                setIsRegistrationSuccessful(true);
                return response.json();
            }
        }).then(data => {
            console.log(data);
        });
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
                    {/* TITLE */}
                    <Typography variant="h5" textAlign="center">Register</Typography>

                    {/* USERNAME */}
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
                        required
                        error={!!errors.username}
                        helperText={errors.username ? errors.username.message : null}
                    />

                    <TextField
                        label="Email"
                        {...register("email", {
                            required:"Type in email",

                            pattern: {
                                value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
                                message: "Invalid email"
                            }
                        })}
                        required
                        error={!!errors.email}
                        helperText={errors.email ? errors.email.message : null}
                    />

                    {/* PASSWORD */}
                    <TextField
                        label="Password"
                        type="password"
                        {...register("password", {
                            required: "Password is required",
                            minLength: {
                                value: 8,
                                message: "Password must have at least 8 characters"
                            },
                            pattern: {
                                value: /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/,
                                message: "Password must contain lowercase, uppercase, and a number"
                            },
                            validate: (value) =>
                                !/[<>&'"`]|script|select|union|drop|insert/i.test(value) ||
                                "Password cannot have these special characters"

                        })}
                        required
                        error={!!errors.password}
                        helperText={errors.password ? errors.password.message : null}
                        onFocus={() => setIsPasswordFocused(true)}
                        onBlur={() => setIsPasswordFocused(false)}
                    />

                    {/* PASSWORD CONFIRMATION */}
                    <TextField
                        label="Confirm Password"
                        type="password"
                        {...register("confirmPassword", {
                            required: "Rewrite password",
                            validate: (value) =>
                                value === getValues('password') || "Passwords must be the same",
                        })}
                        required
                        error={!!errors.confirmPassword}
                        helperText={errors.confirmPassword ? errors.confirmPassword.message : null}
                    />

                    {/* PASSWORD RULES */}
                    <Box
                        sx={{
                            mt: 0,
                            p: 2,
                            border: '1px solid #ccc',
                            borderRadius: 1,
                            visibility: isPasswordFocused ? 'visible' : 'hidden',
                            opacity: isPasswordFocused ? 1 : 0,
                            transition: 'opacity 0.3s',
                        }}
                    >
                        <Typography variant="subtitle1" gutterBottom sx={{ fontWeight: 'bold' }}>
                            Password must contain:
                        </Typography>

                        {Object.entries(validationCriteria).map(([key, isSatisfied]) => (
                            <Box key={key} sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
                                <Typography
                                    component="span"
                                    sx={{
                                        color: isSatisfied ? 'green' : 'red',
                                        mr: 1,
                                        fontWeight: 'bold'
                                    }}
                                >
                                    {isSatisfied ? '✓' : '✗'}
                                </Typography>
                                <Typography
                                    variant="body2"
                                    sx={{ color: isSatisfied ? 'green' : 'red' }}
                                >
                                    {
                                        key === 'lowercase' ? 'A lowercase letter' :
                                            key === 'uppercase' ? 'A capital (uppercase) letter' :
                                                key === 'number' ? 'A number' :
                                                    'Minimum 8 characters'
                                    }
                                </Typography>
                            </Box>
                        ))}
                    </Box>

                    {/* SUBMIT */}
                    <Button type="submit" variant="contained" color="primary">
                        Register
                    </Button>
                </Box>
            )}
        </Box>
    );
}

export default RegistrationPage;