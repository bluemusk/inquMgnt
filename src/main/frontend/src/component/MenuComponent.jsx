// Handles display of the top menu.

import React from 'react'
import { Link, withRouter } from 'react-router-dom'
import AuthenticationService from '../service/AuthenticationService';
function MenuComponent() {
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <div><a href="http://localhost:8080" className="navbar-brand">kakaopay</a></div>
                <ul className="navbar-nav">
                    <li><Link className="nav-link" to="/clserLogin">Counselor</Link></li>
                    <li><Link className="nav-link" to="/userInit">User</Link></li>
                </ul>
                <ul className="navbar-nav navbar-collapse justify-content-end">
                    {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>}
                </ul>
            </nav>
        </header>
    )
}
export default withRouter(MenuComponent)