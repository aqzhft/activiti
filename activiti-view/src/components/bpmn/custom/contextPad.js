import TaskConfig from './taskConfig'
import FlowConfig from './flowConfig'
import ReactDOM from 'react-dom/client';

export default class ContextPad {
    constructor(config, contextPad, create, elementFactory, injector, translate, modeling, moddle) {
        this.create = create;
        this.elementFactory = elementFactory;
        this.translate = translate;
        this.modeling = modeling;
        this.moddle = moddle;

        if (config.autoPlace !== false) {
            this.autoPlace = injector.get('autoPlace', false);
        }

        contextPad.registerProvider(this);
    }

    getContextPadEntries(element) {
        // TODO:const { autoPlace, create, elementFactory, translate } = this;
        const { translate } = this;

        // function appendServiceTask(event, element) {
        //     if (autoPlace) {
        //         const shape = elementFactory.createShape({ type: 'bpmn:ServiceTask' });
        //         autoPlace.append(element, shape);
        //     } else {
        //         appendServiceTaskStart(event, element);
        //     }
        // }

        // function appendServiceTaskStart(event) {
        //     const shape = elementFactory.createShape({ type: 'bpmn:ServiceTask' });
        //     create.start(event, shape, element);
        // }

        const customConfig = (event, element) => {

            console.log(element)

            switch (element.type) {
                case 'bpmn:UserTask': 
                    configUser(element);break;
                case 'bpmn:SequenceFlow':
                    configSequenceFlow(element);break;
                default: break;
            }
        }

        const configSequenceFlow = (element) => {
            let dom = document.createElement('div')
            let app = document.getElementById('root')
            app.appendChild(dom)
            let businessObject = element.businessObject;
            
            let data = {
                condition: ''
            }
            let condition = businessObject.get('conditionExpression')
            if (condition && condition.body) {
                data.condition = condition.body
            }
            
            ReactDOM.createRoot(dom).render(
                <FlowConfig data={data} callback={(value) => setCondition(value, element)} cancel={() => removeConfiUser(dom)}/>
            );
        }

        const setCondition = (value, element) => {
            let newCondition = this.moddle.create('bpmn:FormalExpression', {
                body: value.condition
            });
            this.modeling.updateProperties(element, {
                conditionExpression: newCondition
            });
        }

        const configUser = (element) => {
            let dom = document.createElement('div')
            let app = document.getElementById('root')
            app.appendChild(dom)

            let businessObject = element.businessObject;

            let data = {
                assignee: businessObject.assignee,
                candidates: businessObject.candidateUsers?.split(','),
                groupIds: businessObject.candidateGroups?.split(',')
            }

            ReactDOM.createRoot(dom).render(
                <TaskConfig data={data} callback={(value) => printUserList(value, element)} cancel={() => removeConfiUser(dom)}/>
            );
        }

        const removeConfiUser = (dom) => {
            dom.parentNode.removeChild(dom);
        }

        const printUserList = (value, element) => {
            let businessObject = element.businessObject;

            console.log(value);
            businessObject.assignee = value.assignee
            businessObject.candidateUsers = value.candidates.join(',')
            businessObject.candidateGroups = value.groupIds.join(',')
        }

        return {
            'append.service-task': {
                group: 'model',
                className: 'bpmn-icon-service-task',
                title: translate('Append ServiceTask'),
                action: {
                    click: customConfig
                }
            }
        };
    }
}

ContextPad.$inject = [
    'config',
    'contextPad',
    'create',
    'elementFactory',
    'injector',
    'translate',
    'modeling',
    'moddle',
];