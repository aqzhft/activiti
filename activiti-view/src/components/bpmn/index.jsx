import BpmnModeler from 'bpmn-js/lib/Modeler';
import React, { useEffect, useState } from 'react';
// import { xmlstr } from '../../assets/initDiagram';
import assigneePackage from './custom/assignee';

import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
import 'bpmn-js/dist/assets/bpmn-js.css';
import 'bpmn-js/dist/assets/diagram-js.css';
// 右侧属性面包样式
import { Button, Space } from 'antd';
import axios from 'axios';
import { useSearchParams } from 'react-router-dom';
import customControlsModule from './custom';

const App = () => {

    const [bpmnModeler, setBpmnModeler] = useState(null)
    const [search] = useSearchParams()

    const saveSvg = () => {
        let key = search.get('processKey')
        bpmnModeler.saveSVG().then(response => {
            var url = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(response.svg);
            let link = document.createElement("a");
            link.download = `${key}.svg`
            link.href = url;
            link.click()
        })
    }

    const saveXml = () => {
        let key = search.get('processKey')
        bpmnModeler.saveXML({}).then(response => {
            const xml = new Blob([response.xml], { type: 'application/xml' })
            let link = document.createElement("a");
            link.download = `${key}.xml`
            link.href = URL.createObjectURL(xml);
            link.click()
        })
    }

    const deploy = () => {
        let key = search.get('processKey')
        bpmnModeler.saveXML({}).then(response => {
            const xml = response.xml
            axios.post('/api/process/xml', { data: xml, fileName: `${key}.bpmn`, name: search.get('processName'), key: search.get('processKey') }).then(response => {
                window.close()
            })
        })
    }

    useEffect(() => {
        const modeler = new BpmnModeler({
            container: '#canvas', // 这里为数组的第一个元素
            height: '700px',
            propertiesPanel: {
                parent: '.properties-panel'
            },
            moddleExtensions: {
                assignee: assigneePackage
            },
            additionalModules: [
                customControlsModule
            ]
        });
        let key = search.get('processKey')
        axios.get('/api/process/xml/key/' + key).then(response => {
            modeler.importXML(response.data)
        })
        modeler.on('element.click', (event) => {
            modeler.saveXML({}).then(response => {
                console.log(response)
            })
        })

        setBpmnModeler(modeler)
    }, [search])

    return (
        <>
            <Space>
                <Button onClick={() => saveSvg()}>保存图片</Button>
                <Button onClick={() => saveXml()}>保存文件</Button>
                <Button onClick={() => deploy()}>发布</Button>
            </Space>
            {/* bpmn容器 */}
            <div id="canvas" className="container"></div>
            <div id='toolbox' className='properties-panel'></div>
        </>
    )
}

export default App
