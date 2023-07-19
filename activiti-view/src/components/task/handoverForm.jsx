import { Button, Modal, Input, Form, Select } from 'antd';
import axios from 'axios';
import { useState } from 'react';

const App = ({ taskId, userId }) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [userList, setUserList] = useState([]);
    const showModal = () => {
        setOpen(true);
        getUserList(taskId);
    }
    const handleOk = () => {
        let value = form.getFieldsValue()
        setConfirmLoading(true);
        axios.post('/api/task/handover/' + taskId + "?comment=" + value.comment + "&userId=" + userId + "&targetUserId=" + value.targetUserId).then(response => {
            setOpen(false);
            setConfirmLoading(false);
            // callback();
        })
    };
    const handleCancel = () => {
        setOpen(false);
    };

    const { TextArea } = Input

    const onChange = (value) => {
        console.log(value);
    }

    const onSearch = (value) => {
        console.log(value);
    }

    const getUserList = (taskId) => {
        axios.get('/api/task/candidate/' + taskId).then(response => {
            setUserList(response.data.map(bean => {
                return {
                    label: bean.userName,
                    value: bean.userId
                }
            }));
        })
    }

    return (
        <>
            <Button onClick={showModal}>转发</Button>
            <Modal
                title="转发"
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
                    <Form.Item name="targetUserId">
                        <Select
                            showSearch
                            placeholder="选择人员"
                            optionFilterProp="children"
                            onChange={onChange}
                            onSearch={onSearch}
                            filterOption={(input, option) =>
                                (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
                            }
                            options={userList}
                        />
                    </Form.Item>
                    <Form.Item name="comment">
                        <TextArea
                            placeholder="转发说明"
                            style={{ height: 120, resize: 'none' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default App;