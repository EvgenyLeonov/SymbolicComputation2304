const imgFolder = 'img/';
const nodes = [
{ id: 1, shape: 'circularImage', image: imgFolder + 'independence.jpg', label: 'Independence City'  },
{ id: 2, shape: 'circularImage', image: imgFolder + 'chimney_rock.jpg', label: 'Chimney Rock'  },
{ id: 3, shape: 'circularImage', image: imgFolder + 'fort_laramie.jpg', label: 'Fort Laramie'  },
{ id: 4, shape: 'circularImage', image: imgFolder + 'canyon.jpg', label: 'Pine Canyon'  },
{ id: 5, shape: 'circularImage', image: imgFolder + 'native_americans_attack.jpg', label: 'Ambush!'  },
{ id: 6, shape: 'circularImage', image: imgFolder + 'trade_post.jpg', label: 'Trade Post'  },
{ id: 7, shape: 'circularImage', image: imgFolder + 'colorado.jpg', label: 'Colorado River'  },
{ id: 8, shape: 'circularImage', image: imgFolder + 'donner_pass.jpg', label: 'Donner Pass'  },
{ id: 9, shape: 'circularImage', image: imgFolder + 'salt_desert.jpg', label: 'Salt Desert'  },
{ id: 10, shape: 'circularImage', image: imgFolder + 'oregon_city.jpg', label: 'Oregon City'  }];
const edges = [
{ from: 1, to: 2, label: '-30/7' },
{ from: 1, to: 3, label: '-20/10' },
{ from: 2, to: 4, label: '-20/4' },
{ from: 3, to: 4, label: '-10/5' },
{ from: 4, to: 5, label: '-40/4' },
{ from: 4, to: 6, label: '+40/10' },
{ from: 5, to: 7, label: '-20/8' },
{ from: 6, to: 7, label: '-30/10' },
{ from: 7, to: 8, label: '-25/12' },
{ from: 7, to: 9, label: '-20/8' },
{ from: 8, to: 10, label: '-10/6' },
{ from: 9, to: 10, label: '-20/4' }];
