import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { useState } from 'react';
import axios from 'axios';
const App = ({ callback }) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const showModal = () => {
        setOpen(true);
    };
    const handleOk = () => {
        setConfirmLoading(true);
        axios.post('/busi/hazard', form.getFieldsValue()).then(response => {
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
            <Button type="primary" onClick={showModal}>新增报告单</Button>
            <Modal
                title="隐患报告"
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
                    <Form.Item label="名称" name="name">
                        <Input />
                    </Form.Item>
                    <Form.Item label="描述" name="reportDesc">
                        <TextArea
                            placeholder="描述信息"
                            style={{ height: 120, resize: 'none' }}
                        />
                    </Form.Item>
                    <Form.Item label="请假天数" name={'daySize'}>
                        <Input />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
};
export default App;
