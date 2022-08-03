// Login Component representing the login screen.
import React from 'react';
import AuthenticationService from '../service/AuthenticationService';
import { useState } from 'react';
export default function LoginComponent(props) {
    const [state, setState] = useState({
        username: '',
        password: '',
        hasLoginFailed: false,
        showSuccessMessage: false
    });

    const handleChange = (event) => {
        setState({
            ...state,
            [event.target.name]: event.target.value
        });
    };

    const loginClicked = () => {
        AuthenticationService.executeBasicAuthenticationService(state.username, state.password)
            .then(() => {
                debugger;
                AuthenticationService.registerSuccessfulLogin(state.username, state.password);
                props.history.push('/inquYn')
            }).catch(e => {
            console.log(e);
            setState(
                {
                    ...state,
                    showSuccessMessage: false,
                    hasLoginFailed: true
                }
            );
        });
    };

    return (
        <div align="left">
            <h1>Counselor Login</h1>
            <div className="container">
                <table className="table" border="0" cellSpacing="0" cellPadding="0">
                    <colgroup>
                        <col width="20%"/>
                        <col width="80%"/>
                    </colgroup>
                    <tr>
                        <td colSpan="2">
                            {state.hasLoginFailed && <div className="alert alert-warning">로그인 정보가 일치하지 않습니다.</div>}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            User Name:
                        </td>
                        <td>
                            <input type="text" name="username" value={state.username} onChange={handleChange}></input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password:
                        </td>
                        <td>
                            <input type="password" name="password" value={state.password} onChange={handleChange}></input>
                        </td>
                    </tr>
                    <tr>
                      <button className="btn btn-success" onClick={loginClicked}>Login</button>
                    </tr>
                </table>
            </div>
        </div>
    );
}