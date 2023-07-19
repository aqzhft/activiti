import { Form, Input, Modal } from "antd";
import { useEffect, useState } from 'react';

const App = (props) => {
    const [form] = Form.useForm();
    const [open, setOpen] = useState(true)
    const [confirmLoading, setConfirmLoading] = useState(false);

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

    useEffect(() => {
        form.setFieldsValue(props.data)
    }, [form, props.data])

    return (
        <>
            <Modal
                title="条件配置"
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
                    <Form.Item label="条件表达式" name="condition">
                        <Input />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default App
