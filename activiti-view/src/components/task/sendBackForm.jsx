import { Button, Modal, Input, Form } from 'antd';
import axios from 'axios';
import { useState } from 'react';

const App = ({ taskId, userId }) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const showModal = () => {
        setOpen(true);
    }
    const handleOk = () => {
        let value = form.getFieldsValue()
        setConfirmLoading(true);
        axios.delete('/api/task/sendBack/' + taskId + "?comment=" + value.comment + '&userId=' + userId).then(response => {
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
            <Button onClick={showModal}>退回</Button>
            <Modal
                title="退回"
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
                            placeholder="退回说明"
                            style={{ height: 120, resize: 'none' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default App;