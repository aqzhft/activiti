import React from 'react'
import './index.css'
import UserTaskView from './userTask'

class TaskView extends React.Component {
    render() {
        return (
            <div className="view">
                <UserTaskView userId="zhangsan"></UserTaskView>
                <UserTaskView userId="lisi"></UserTaskView>
            </div>
        )
    }
}

export default TaskView