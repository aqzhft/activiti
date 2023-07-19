import { Button, Modal, Input, Form } from 'antd';
import axios from 'axios';
import { useState } from 'react';

const App = ({ taskId }) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const showModal = () => {
        setOpen(true);
    }
    const handleOk = () => {
        let value = form.getFieldsValue()
        setConfirmLoading(true);
        axios.post('/api/task/complete/' + taskId + "?comment=" + value.comment).then(response => {
            setOpen(false);
            setConfirmLoading(false);
            // callback();
        })
    };
    const handleCancel = () => {
        setOpen(false);
    };

    const { TextArea } = Input

    return (
        <>
            <Button type="primary" onClick={showModal}>处理任务</Button>
            <Modal
                title="审批"
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
                    <Form.Item name="comment">
                        <TextArea
                            placeholder="评论信息"
                            style={{ height: 120, resize: 'none' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default App;