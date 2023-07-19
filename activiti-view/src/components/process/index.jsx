import { SettingOutlined } from '@ant-design/icons';
import { Card, Tabs } from 'antd';
import axios from 'axios';
import React from 'react';
import InstanceView from '../instance';
import Runtime from '../instance/runtime';
import './index.css';

class ProcessView extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            defineList: [],
            currentDefine: null
        }
    }

    componentDidMount() {
        this.getProcessList()
    }

    setCurrentDefine (define) {
        this.setState({ currentDefine: define})
    }

    getProcessList() {
        axios.get('/api/process/define').then(response => {
            let result = response.data
            if (result && result.length > 0) {
                this.setCurrentDefine(result[0])
            }
            this.setState({ defineList: result })
        })
    }

    showBpmn = (processKey, processName, e) => {
        e.stopPropagation();
        let height = window.innerHeight > 600 ? window.innerHeight * 0.5 : 600
        let width = window.innerWidth > 1000 ? window.innerWidth * 0.5 : 1000
        let top = (window.innerHeight - height) / 4
        let left = (window.innerWidth - width) / 4
        window.open('/bpmn?processKey=' + processKey + '&processName=' + processName, '_blank', 'location=no,width=' + width + 'px,height=' + height + 'px,top=' + top + 'px,left=' + left + 'px')
    }

    renderProcessList(defineList) {
        return defineList.map((define, index) => (
            <Card className="process" key={index} onClick={() => this.setCurrentDefine(define)}>
                <div>流程名称：{define.name}</div>
                <div>当前实例：{define?.instanceList?.length}</div>
                <SettingOutlined onClick={(e) => this.showBpmn(define.key, define.name, e)}>配置</SettingOutlined>
            </Card>
        ))
    }

    tabItems() {
        const { currentDefine } = this.state
        return currentDefine && [
            {
                key: 1,
                label: '当前流转进程',
                children: <Runtime processKey={currentDefine.key} />
            },
            {
                key: 2,
                label: '历史流转记录',
                children: <InstanceView processKey={currentDefine.key} />
            }
        ]
    }

    render() {
        const { defineList } = this.state
        return (
            <div className="process-view">
                <div className="process-list">{this.renderProcessList(defineList)}</div>
                <Card>
                    <Tabs defaultActiveKey='1' items={this.tabItems()}></Tabs>
                </Card>
            </div>
        )
    }
}

export default ProcessView