import { registerPlugin } from '@capacitor/core';

import type { CapacitorUsercentricsPlugin } from './definitions';

const CapacitorUsercentrics = registerPlugin<CapacitorUsercentricsPlugin>('CapacitorUsercentrics', {
  web: () => import('./web').then((m) => new m.CapacitorUsercentricsWeb()),
});

export * from './definitions';
export { CapacitorUsercentrics };
