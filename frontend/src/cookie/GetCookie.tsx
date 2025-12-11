import { Cookies } from 'react-cookie';

export function SetCookie(cookieObject: object) {
    const cookies = new Cookies();
    cookies.set('biscuit', cookieObject, {path: "/", maxAge: 7200000});
    sessionStorage.setItem('logged', String(true));
}

export function GetCookie() {
    const cookies = new Cookies();
    const cookie = cookies.get('biscuit');
    if (cookie !== undefined) {
        SetCookie(cookie);
        return cookie;
    }
    if (sessionStorage.getItem('logged') === "true") {
        sessionStorage.setItem('logged', String(false));
        alert("Your session has expired");
    }
    return false;
}

export function ClearCookie() {
    const cookies = new Cookies();
    sessionStorage.setItem('logged', String(false));
    cookies.remove('biscuit');
}