import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { useState } from 'react';
import axios from 'axios';
const App = ({callback}) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const showModal = () => {
        setOpen(true);
    };
    const handleOk = () => {
        setConfirmLoading(true);
        axios.post('/busi/holiday', form.getFieldsValue()).then(response => {
            setOpen(false);
            setConfirmLoading(false);
            callback();
        })
    };
    const handleCancel = () => {
        setOpen(false);
    };
    const { TextArea } = Input

    return (
        <>
            <Button type="primary" onClick={showModal}>新增申请单</Button>
            <Modal
                title="请假申请"
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
                    style={{
                        maxWidth: 600,
                    }}
                    form={form}
                >
                    <Form.Item label="申请人ID" name="applyUserId">
                        <Input />
                    </Form.Item>
                    <Form.Item label="申请人姓名" name="applyUserName">
                        <Input />
                    </Form.Item>
                    <Form.Item label="请假天数" name={'daySize'}>
                        <InputNumber min={1} max={10} />
                    </Form.Item>
                    <Form.Item name="description">
                        <TextArea
                            placeholder="描述信息"
                            style={{ height: 120, resize: 'none' }}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
};
export default App;
