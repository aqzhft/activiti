import { Button, Modal, Timeline } from 'antd';
import axios from 'axios';
import moment from 'moment';
import { useState } from 'react';
import TaskConfigView from '../config/form'
const App = ({ instanceId }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [timeLineItemList, setItemList] = useState([]);
    const showInstanceLog = (instanceId) => {
        axios.get('/api/log?instanceId=' + instanceId).then(response => {
            setItemList(response.data.map(bean => {
                return {
                    label: bean.endTime ? moment(new Date(bean.endTime)).format('YYYY-MM-DD HH:mm') : "进行中",
                    children: (
                        <>
                            <div>{bean.name}</div>
                            <div>{bean.executorId ? ("操作人： " + bean.executorId)  : ""}</div>
                            <div>{bean.endTime ? "" : (
                                <TaskConfigView taskId={bean.id}>配置</TaskConfigView>
                            )}</div>
                        </>
                    )
                }
            }))
        })
    }
    const showModal = () => {
        setIsModalOpen(true);
        showInstanceLog(instanceId)
    };
    return (
        <>
            <Button type="primary" onClick={showModal}>
                查看进度
            </Button>
            <Modal title="任务执行进度" open={isModalOpen} footer={[]} onCancel={() => setIsModalOpen(false)}>
                <Timeline
                    mode="left"
                    items={timeLineItemList}
                />
            </Modal>
        </>
    );
};
export default App;