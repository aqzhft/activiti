import { Button, Space } from 'antd'
import axios from 'axios'
import React from 'react'
import CompleteForm from './completeForm'
import HandoverForm from './handoverForm'
import SendBackForm from './sendBackForm'

class UserTaskView extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            // 我的任务
            mineList: [],
            // 任务广场
            marketList: []
        }
    }

    componentDidMount() {
        this.getMineTaskList()
        this.getMarketTaskList()
    }

    getMineTaskList = () => {
        axios.get("/api/task/assignee?userId=" + this.props.userId).then(response => {
            this.setState({
                mineList: response.data
            })
        })
    }

    getMarketTaskList = () => {
        axios.get("/api/task/candidate?userId=" + this.props.userId).then(response => {
            this.setState({
                marketList: response.data
            })
        })
    }

    claim = (taskId) => {
        axios.post('/api/task/claim?userId=' + this.props.userId + '&taskId=' + taskId).then(response => {
            this.getMarketTaskList()
            this.getMineTaskList()
        })
    }

    handoverOnchange = (value) => {
        console.log(value)
    }

    handoverOnSearch = (value) => {
        console.log(value)
    }

    renderTaskList(taskList) {
        return taskList.map((task, index) => (
            <div className="item" key={index}>
                <div className="instance-key">{task.instanceKey}</div>
                <div className="process-name">流程名称：{task.processName}</div>
                <div className="instance-name">申请信息：{task.instanceName}</div>
                <div className='task-name'>任务描述：{task.name}</div>
                <div className='task-btns'>
                    <Space>
                        <CompleteForm key={index} taskId={task.id} />
                        <HandoverForm key={index + 1000} taskId={task.id} userId={this.props.userId}>转发</HandoverForm>
                        <SendBackForm key={index + 10000} taskId={task.id} userId={this.props.userId}>放弃</SendBackForm>
                    </Space>
                </div>
            </div>
        ))
    }

    renderMarketTaskList(taskList) {
        return taskList.map((task, index) => (
            <div className="item" key={index}>
                <div className="instance-key">{task.instanceKey}</div>
                <div className="process-name">流程名称：{task.processName}</div>
                <div className="instance-name">申请信息：{task.instanceName}</div>
                <div className='task-name'>任务描述：{task.name}</div>
                <div className='task-btns'>
                    <Button type="primary" onClick={() => this.claim(task.id)}>领取</Button>
                </div>
            </div>
        ))
    }

    render() {
        const { mineList, marketList } = this.state
        return (
            <div className="task-view">
                <div className="panel market">
                    <div className="title">任务广场 [ {this.props.userId} ]</div>
                    <div className='list'>
                        {this.renderMarketTaskList(marketList)}
                    </div>
                </div>
                <div className="panel mine">
                    <div className="title">我的任务 [ {this.props.userId} ]</div>
                    <div className='list'>
                        {this.renderTaskList(mineList)}
                    </div>
                </div>
            </div>
        )
    }

}

export default UserTaskView