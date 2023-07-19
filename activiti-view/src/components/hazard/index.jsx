import { Button, Space, Table } from 'antd'
import axios from 'axios'
import { useEffect, useState } from 'react'
import './index.css'
import ReportForm from './reportForm'

const App = (props) => {

    const [reportList, setReportList] = useState([])

    const columns = [
        {
            title: '序号',
            dataIndex: 'index',
            key: 'index',
        },
        {
            title: '报告编号',
            dataIndex: 'hazardKey',
            key: 'hazardKey',
        },
        {
            title: '名称',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '描述',
            dataIndex: 'reportDesc',
            key: 'reportDesc',
        },
        {
            title: '紧急程度',
            dataIndex: 'level',
            key: 'level',
        },
        {
            title: '位置',
            dataIndex: 'position',
            key: 'position',
        },
        {
            title: '报告人',
            dataIndex: 'applyUserName',
            key: 'applyUserName',
        },
        {
            title: '报告时间',
            dataIndex: 'applyUserName',
            key: 'applyUserName',
        },
        {
            title: '状态',
            dataIndex: 'status',
            key: 'status'
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => record.submitted ? (
                <span>已提交</span>
            ) : (
                <Space size="middle">
                    <Button onClick={() => this.submit(record.id)}>提交</Button>
                    <Button onClick={() => this.submit(record.id)}>评估</Button>
                    <Button onClick={() => this.submit(record.id)}>整改</Button>
                    <Button onClick={() => this.submit(record.id)}>验收</Button>
                </Space>
            )
        }
    ]

    const reportShowList = (reportList) => {
        return reportList.map((report, index) => {
            return {
                key: index,
                index: index + 1,
                id: report.id,
                hazardKey: report.hazardKey,
                name: report.name,
                reportDesc: report.reportDesc,
                level: report.level,
                position: report.position,
                status: '待提交'
            }
        })
    }

    const getReportList = () => {
        axios.get('/busi/hazard/report').then(response => {
            setReportList(response.data)
        })
    }

    useEffect(() => {
        getReportList()
    }, [])

    return (
        <div className="view">
            <div className="header">
                <ReportForm/>
            </div>
            <div className="main">
                <Table dataSource={reportShowList(reportList)} columns={columns} />
            </div>
        </div>
    )
}

export default App
