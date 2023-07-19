import { Form, Modal, Select } from "antd";
import axios from "axios";
import { useEffect, useState } from 'react';

const App = (props) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(true)
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [participants, setParticipants] = useState([]);
    const [groupIds, setGroupIds] = useState([]);

    const handleOk = () => {
        let value = form.getFieldsValue()
        setConfirmLoading(true);
        props.callback(value)
        setOpen(false)
    };

    const handleCancel = () => {
        setOpen(false);
        props.cancel()
    };

    const getAllParticipants = () => {
        axios.get('/api/participant').then(response => {
            setParticipants(response.data)
        })
    }

    const getAllGroupIds = () => {
        axios.get('/api/participant/group').then(response => {
            setGroupIds(response.data)
        })
    }

    const getSelectList = () => {
        return participants.map(bean => {
            return {
                value: bean.userId,
                label: bean.userName
            }
        })
    }

    const getGroupSelectList = () => {
        return groupIds.map(groupId => {
            return {
                value: groupId,
                label: groupId
            }
        })
    }

    useEffect(() => {
        getAllParticipants();
        getAllGroupIds();
        form.setFieldsValue(props.data)
    }, [form, props.data])

    return (
        <>
            <Modal
                title="任务配置"
                open={open}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}
                okText="保存"
                cancelText="取消"
            >
                <Form
                    labelCol={{
                        span: 4,
                    }}
                    wrapperCol={{
                        span: 14,
                    }}
                    layout="horizontal"
                    form={form}
                >
                    <Form.Item name="assignee">
                        <Select options={getSelectList()}></Select>
                    </Form.Item>
                    <Form.Item name="candidates">
                        <Select mode="multiple" options={getSelectList()}></Select>
                    </Form.Item>
                    <Form.Item name="groupIds">
                        <Select mode="multiple" options={getGroupSelectList()}></Select>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default App
