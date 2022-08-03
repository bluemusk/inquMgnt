// React Component representing the high-level structure of the application. Routing is defined in this file.
import React from 'react';
import InquListComponent from './InquListComponent';
import AuthenticatedRoute from './AuthenticatedRoute';
import MenuComponent from './MenuComponent';
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import UserFuncComponent from "./UserFuncComponent";
// import SetInquComponent from "./SetInquComponent";
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
// import { Routes, Route } from 'react-router-dom';
function CounselorApp() {
    return (
        <>
            <Router>
                <>
                    <MenuComponent />
                    <Switch>
                        <Route path="/" exact component={LoginComponent} />
                        <AuthenticatedRoute path="/logout" exact component={LogoutComponent} />
                        <AuthenticatedRoute path="/inquYn" exact component={InquListComponent} />
                        <AuthenticatedRoute path="/userInit" exact component={UserFuncComponent} />
                        {/*<AuthenticatedRoute path="/new" exact component={SetInquComponent} />*/}
                    </Switch>
                </>
            </Router>
        </>
    );
}

export default CounselorApp;