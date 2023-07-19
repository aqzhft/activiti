import CustomContextPad from './contextPad';
// import CustomPalette from './CustomPalette';



// export default {
//     __init__: ['customContextPad', 'customPalette'],
//     customContextPad: ['type', CustomContextPad],
//     customPalette: ['type', CustomPalette]
// };

const Custom = {
    __init__: ['customContextPad'],
    customContextPad: ['type', CustomContextPad],
    // customPalette: ['type', CustomPalette]
}

export default Custom