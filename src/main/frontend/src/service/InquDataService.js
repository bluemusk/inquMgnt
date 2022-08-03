import axios from 'axios';
import React, { Component } from 'react';

class InquDataService extends Component{
    constructor() {
        super();
    }

    retrieveAllInqus(name) {
        return axios.get('http://localhost:8080/kakaopay/inquYn',
        );
    }
}
export default new InquDataService;