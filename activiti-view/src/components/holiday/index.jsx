import { Table, Space, Button } from 'antd';
import moment from 'moment';
import React from "react";
import HolidayForm from './form';

import axios from "axios";

class HolidayView extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            applyList: []
        }
    }

    columns = [
        {
            title: '序号',
            dataIndex: 'index',
            key: 'index',
        },
        {
            title: '申请编号',
            dataIndex: 'holidayKey',
            key: 'holidayKey',
        },
        {
            title: '申请人ID',
            dataIndex: 'applyUserId',
            key: 'applyUserId',
        },
        {
            title: '申请人姓名',
            dataIndex: 'applyUserName',
            key: 'applyUserName',
        },
        {
            title: '申请时间',
            dataIndex: 'applyTime',
            key: 'applyTime',
        },
        {
            title: '说明',
            dataIndex: 'description',
            key: 'description',
        },
        {
            title: '当前状态',
            dataIndex: 'status',
            key: 'status',
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => record.submitted ? (
                <span>已提交</span>
            ) : (
                <Space size="middle">
                    <Button onClick={() => this.submit(record.id)}>提交</Button>
                </Space>
            )
        }
    ]

    componentDidMount() {
        this.getHolidayApplyList()
    }

    getHolidayApplyList() {
        axios.get("/busi/holiday").then(response => {
            this.setState({ applyList: response.data })
        })
    }

    showHolidayApplyList(dataList) {
        return dataList.map((data, index) => {
            return {
                key: index,
                index: index + 1,
                id: data.id,
                applyUserId: data.applyUserId,
                applyUserName: data.applyUserName,
                applyTime: moment(new Date(data.applyTime)).format('YYYY-MM-DD HH:mm'),
                description: data.description,
                submitted: data.submitted,
                holidayKey: data.holidayKey
            }
        })
    }

    submit = (id) => {
        axios.post('/busi/holiday/process/' + id).then(response => {
            this.getHolidayApplyList()
        })
    }

    render() {
        const { applyList } = this.state
        let applyShowList = this.showHolidayApplyList(applyList)
        return (
            <div className="view">
                <div className="main">
                    <div className="tools">
                        <HolidayForm callback={() => this.getHolidayApplyList()} />
                    </div>
                    <div className="datagrid">
                        <Table dataSource={applyShowList} columns={this.columns} />
                    </div>
                </div>
            </div>
        )
    }
}

export default HolidayView
