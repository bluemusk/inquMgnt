/**
 *  React Component for listing all the inqus for an instructor.
 */
import React from 'react';
import InquDataService from '../service/InquDataService';
import { useEffect } from 'react';
import { useCallback } from 'react';
import { useState } from 'react';

function DtList({data}){
    const [checkedItems, setCheckedItems] = useState(new Set());
    const checkedItemHandler = (id, isChecked) => {
        if (isChecked) {
            checkedItems.add(id);
            setCheckedItems(checkedItems);
        } else if (!isChecked && checkedItems.has(id)) {
            checkedItems.delete(id);
            setCheckedItems(checkedItems);
        }
    };
    // Issue 컴포넌트
    const [bChecked, setChecked] = useState(false);
    const checkHandler = ({ target }) => {
        setChecked(!bChecked);
        checkedItemHandler(data.cmpl_yn, target.checked);
    };

    return (
        <tr align="left">
            ID : {data.inqu_id}<br/>
            문의제목 : {data.inqu_titl}<br/>
            문의내용 : {data.inqu_cn}<br/>
            문의일시 : {data.input_dthms}<br/>
            답변하기 선택 : <input type="checkbox" checked={bChecked} onChange={(e) => checkHandler(e)} />
        </tr>
    )
}

export default function ListInqusComponent() {
    const [inqus, setInqus] = useState([]);
    const [message, setMessage] = useState(null);
    const refreshInqus = useCallback(() => {
        InquDataService.retrieveAllInqus().then(
            response => {
                console.log(response);
                setInqus(response.data);
            }
        );
    }, []);
    const searchClicked = () => {
        InquDataService.retrieveAllInqus().then(
            response => {
                console.log(response);
                setInqus(response.data);
            }
        );
    };

    useEffect(() => {
        refreshInqus();
    }, [refreshInqus]);
    return (
        <div align="left" className="container">
            <h3>사용자가 답변을 기다리는 문의</h3>
            {/*<button className="btn btn-search" onClick={searchClicked} style="background-color: blue">Search</button>*/}
            <div className="container">
                <table className="table">
                    <thead>
                    <tr align="left">문의내역
                        {/*<th align="left"></th>*/}
                        {/*<th>문의내용</th>*/}
                        {/*<th>문의일시</th>*/}
                    </tr>
                    </thead>
                    <tbody>
                    {inqus.map(inqu =><DtList data={inqu}/>)}
                    </tbody>
                </table>
            </div>
        </div>
    );
}