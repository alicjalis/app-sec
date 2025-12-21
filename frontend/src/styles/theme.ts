import { createTheme, type ThemeOptions } from '@mui/material/styles';
import type {PaletteMode} from '@mui/material';

const designTokens = (mode: PaletteMode): ThemeOptions => ({
    palette: {
        mode,
        primary: {
            main: '#646cff',
            light: '#747bff',
            dark: '#535bf2',
        },
        background: {
            default: mode === 'dark' ? '#242424' : '#ffffff',
            paper: mode === 'dark' ? '#1a1a1a' : '#f9f9f9',
        },
        text: {
            primary: mode === 'dark' ? 'rgba(255, 255, 255, 0.87)' : '#213547',
        },
    },
    typography: {
        fontFamily: 'system-ui, Avenir, Helvetica, Arial, sans-serif',
        button: {
            textTransform: 'none' as const,
            fontWeight: 500,
        },
    },
    components: {
        MuiButton: {
            styleOverrides: {
                root: {
                    borderRadius: '8px',
                    padding: '0.6em 1.2em',
                    fontSize: '1em',
                    transition: 'border-color 0.25s',
                },
                contained: {
                    backgroundColor: mode === 'dark' ? '#1a1a1a' : '#f9f9f9',
                    color: mode === 'dark' ? '#fff' : '#213547',
                    border: '1px solid transparent',
                    '&:hover': {
                        borderColor: '#646cff',
                        backgroundColor: mode === 'dark' ? '#1a1a1a' : '#f9f9f9',
                    },
                },
            },
        },
        MuiTextField: {
            styleOverrides: {
                root: {
                    '& label': { color: '#888' },
                    '& label.Mui-focused': { color: '#646cff' },
                    '& .MuiOutlinedInput-root': {
                        borderRadius: '8px',
                        '& fieldset': { borderColor: '#888' },
                        '&:hover fieldset': { borderColor: '#646cff' },
                    },
                },
            },
        },
    },
});

export const getTheme = (mode: PaletteMode) => createTheme(designTokens(mode));