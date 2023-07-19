import { Space, Table } from 'antd'
import axios from 'axios'
import moment from 'moment'
import { useEffect, useState } from 'react'
import TaskLog from './taskLog'

const App = ({ processKey }) => {

    const [instanceList, setInstanceList] = useState([])

    const columns = [
        {
            title: '序号',
            dataIndex: 'index',
            key: 'index',
        },
        {
            title: '流程编号',
            dataIndex: 'instanceKey',
            key: 'instanceKey',
        },
        {
            title: '版本号',
            dataIndex: 'processVersion',
            key: 'processVersion',
        },
        {
            title: '申请人',
            dataIndex: 'applyUserId',
            key: 'applyUserId',
        },
        {
            title: '申请时间',
            dataIndex: 'applyTime',
            key: 'applyTime',
        },
        {
            title: '说明',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '总耗时',
            dataIndex: 'duration',
            key: 'duration',
        },
        {
            title: '操作',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <TaskLog instanceId={record.id}/>
                </Space>
            )
        }
    ]

    const getInstanceList = (processKey) => {
        axios.get('/api/instance/historic/key?processKey=' + processKey).then(response => {
            setInstanceList(response.data)
        })
    }

    const getDataSource = (dataList) => {
        return dataList.map((data, index) => {
            return {
                key: index,
                index: index + 1,
                applyUserId: data.applyUserId,
                applyTime: moment(new Date(data.applyTime)).format('YYYY-MM-DD HH:mm'),
                name: data.name,
                duration: Math.ceil(data.durationSeconds / 3600) + ' 小时',
                id: data.id,
                instanceKey: data.instanceKey,
                processVersion: data.processVersion
            }
        })
    }

    useEffect(() => {
        getInstanceList(processKey)
    }, [processKey])

    return (
        <>
            <Table dataSource={getDataSource(instanceList)} columns={columns} />
        </>
    )
}

export default App