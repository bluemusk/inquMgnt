import React, {Component} from 'react';
import {Link} from "react-router-dom";

class UserFuncComponent extends Component {
    render() {
        return (
            <div>
                <h1>User Menu</h1>
                <table className="table" border="0" cellSpacing="0" cellPadding="0">
                    <tr>
                        <td>
                            <Link to="/new">새로운 문의글 작성하기</Link>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <Link to="/old">답변 확인하기</Link>
                        </td>
                    </tr>
                </table>
            </div>
        );
    }
}

export default UserFuncComponent;