import {StrictMode, useState, useMemo} from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import {CssBaseline, type PaletteMode, ThemeProvider, useMediaQuery} from "@mui/material";
import { getTheme } from './styles/theme.ts';
import { ColorModeContext } from './context/ColorModeContext.tsx';


export function Root() {
    const systemPrefersDark = useMediaQuery('(prefers-color-scheme: dark)');

    const [mode, setMode] = useState<PaletteMode>(systemPrefersDark ? 'dark' : 'light');

    const colorMode = useMemo(
        () => ({
            mode,
            toggleColorMode: () => {
                setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
            },
        }),
        [mode],
    );

    const theme = useMemo(() => getTheme(mode), [mode]);

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <App />
            </ThemeProvider>
        </ColorModeContext.Provider>
    );
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Root />
      </BrowserRouter>
  </StrictMode>,
)
